package com.pusbin.layanan.internal.services.kategori_instansi;

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
import com.pusbin.layanan.internal.models.KategoriInstansi;
import com.pusbin.layanan.internal.services.kategori_instansi.dto.RequestTambahKategoriInstansi;
import com.pusbin.layanan.internal.services.kategori_instansi.dto.RequestUpdateKategoriInstansi;

@RestController
@RequestMapping("api/v1/kategori_instansi")
public class KategoriInstansiController {

    private final KategoriInstansiService service;

    public KategoriInstansiController(KategoriInstansiService service) {
        this.service = service;
    }

    // GET /kategori_instansi
    @GetMapping
    public List<KategoriInstansi> getAll() {
        return service.getAllKategoriInstansi();
    }

    // GET /kategori_instansi/{id}
    @GetMapping("/{id}")
    public ResponseEntity<KategoriInstansi> getById(@PathVariable Long id) {
        return service.getKategoriInstansiById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /kategori_instansi
    @PostMapping
    public KategoriInstansi create(@RequestBody RequestTambahKategoriInstansi body) {
        return service.createKategoriInstansi(body.getKategoriInstansi());
    }

    // PUT /kategori_instansi/{id}
    @PutMapping("/{id}")
    public ResponseEntity<KategoriInstansi> update(
            @PathVariable Long id, 
            @RequestBody RequestUpdateKategoriInstansi body) {
        try {
            KategoriInstansi updated = service.updateKategoriInstansi(id, body.getKategoriInstansi());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /kategori_instansi/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteKategoriInstansi(id);
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
