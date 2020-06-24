package com.example.service;

import com.example.domain.Brand;
import com.example.domain.BrandExpand;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComsumerService {

    @Test
    public void test(){
        List<BrandExpand> result = new ArrayList<>();
        Brand brand = new Brand(3L, "飞机");
        BrandExpand brandExpand = new BrandExpand(3L, "沈阳飞机工业集团");

        Long brandId = 3L;
        handle(brandId, (b,  be) -> {
            if (b.getBrandId() == brandId) {
                be.setCompany(brandExpand.getCompany() + "2");
            }
            result.add(be);
        });
        result.add(brandExpand);

        result.sort(Comparator.comparingLong(BrandExpand::getBrandId).reversed());
        System.out.println(result);
    }

    // brandId -> brand brandExpand, 收集

    private void handle(Long brandId, Comsumer<Brand, BrandExpand> comsumer) {
        // brandId -> brand brandExpand
        Brand brand = new Brand();
        brand.setBrandId(3L);

        BrandExpand brandExpand = new BrandExpand();
        brandExpand.setBrandId(7L);
        comsumer.apply(brand, brandExpand);
    }

    private static interface Comsumer<A, B> {
        void apply(A a, B b);
    }
}
