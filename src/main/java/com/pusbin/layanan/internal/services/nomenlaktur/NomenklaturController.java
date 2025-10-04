package com.pusbin.layanan.internal.services.nomenlaktur;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pusbin.layanan.common.dto.ApiResponse;
import com.pusbin.layanan.common.dto.GetFilter;
import com.pusbin.layanan.internal.models.Nomenklatur;
import com.pusbin.layanan.internal.services.nomenlaktur.dto.RequestUpdateNomenklatur;


@RestController
@RequestMapping("/api/v1/nomenklatur")
public class NomenklaturController {
    private final NomenklaturService service;

    public NomenklaturController(NomenklaturService service) {
        this.service = service;
    }

    @GetMapping
    public List<Nomenklatur> getAll() {
        return service.getAllNomenklatur();
    }

    @GetMapping("/{id_nomenklatur}")
    public ResponseEntity<Nomenklatur> getById(@PathVariable Long id_nomenklatur) {
        return service.getNomenklaturById(id_nomenklatur)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Nomenklatur create(@RequestBody Nomenklatur body) {
        return service.createNomenklatur(body.getNamaNomenklatur());
    }

    @PutMapping("/{id_nomenklatur}")
    public ResponseEntity<Nomenklatur> update(@PathVariable Long id_nomenklatur, @RequestBody RequestUpdateNomenklatur body) {
        try {
            Nomenklatur updated = service.updateNomenklatur(id_nomenklatur, body.getNama_nomenklatur());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id_nomenklatur}")
    public ResponseEntity<Void> delete(@PathVariable Long id_nomenklatur) {
        service.deleteNomenklatur(id_nomenklatur);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filters")
    public ApiResponse<GetFilter> getFilter() {
     try {
        return ApiResponse.success("OK", service.getFilters());
        } catch (Exception e) {
            return ApiResponse.fail(e.toString());
        }
     }
}
