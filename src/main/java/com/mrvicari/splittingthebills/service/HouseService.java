package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.HouseRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseService
{
    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private TenantRepository tenantRepository;

    public House getCurrentTenantHouse(String email)
    {
        Tenant tenant = tenantRepository.findByEmail(email);

        return houseRepository.findHouseByTenantsContains(tenant);
    }

    public void createHouse(House house, String email)
    {
        Tenant tenant = tenantRepository.findByEmail(email);

        house.getTenants().add(tenant);
        houseRepository.save(house);

        House h = houseRepository.findOne(house.getId());
        tenant.setHouse(h);
        tenantRepository.save(tenant);
    }

    public void joinHouse(String houseNameKeyphrase, String email)
    {
        House house = houseRepository.findHouseByNameKeyphrase(houseNameKeyphrase);
        Tenant tenant = tenantRepository.findByEmail(email);

        house.getTenants().add(tenant);
        houseRepository.save(house);

        tenant.setHouse(house);
        tenantRepository.save(tenant);
    }
}
