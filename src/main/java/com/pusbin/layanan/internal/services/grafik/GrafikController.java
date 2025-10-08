package com.pusbin.layanan.internal.services.grafik;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/grafik")
public class GrafikController {
    
    @GetMapping("/persentase-jf-masn")
    public void persentaseJFMASN() {
        return ;
    }
}
