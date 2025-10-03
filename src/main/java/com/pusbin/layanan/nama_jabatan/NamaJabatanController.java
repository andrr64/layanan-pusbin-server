package com.pusbin.layanan.nama_jabatan;

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

import com.pusbin.common.dto.ApiResponse;
import com.pusbin.common.dto.GetFilter;
import com.pusbin.layanan.nama_jabatan.dto.RequestTambahNamaJabatan;
import com.pusbin.layanan.nama_jabatan.dto.RequestUpdateNamaJabatan;

@RestController
@RequestMapping("/api/v1/nama_jabatan")
public class NamaJabatanController {
    private final NamaJabatanService service;

    public NamaJabatanController(NamaJabatanService service) {
        this.service = service;
    }

    @GetMapping
    public List<NamaJabatan> getAll() {
        return service.getAllNamaJabatan();
    }

    @GetMapping("/{id_nama_jabatan}")
    public ResponseEntity<NamaJabatan> getById(@PathVariable Long id_nama_jabatan) {
        return service.getNamaJabatanById(id_nama_jabatan)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public NamaJabatan create(@RequestBody RequestTambahNamaJabatan body) {
        return service.createNamaJabatan(
            body.getNama_jabatan(),
            Long.valueOf(body.getNomenklatur()) // asumsi dikirim ID nomenklatur
        );
    }

    @PutMapping("/{id_nama_jabatan}")
    public ResponseEntity<NamaJabatan> update(@PathVariable Long id_nama_jabatan, @RequestBody RequestUpdateNamaJabatan body) {
        try {
            NamaJabatan updated = service.updateNamaJabatan(
                id_nama_jabatan,
                body.getNama_jabatan(),
                Long.valueOf(body.getNomenklatur())
            );
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id_nama_jabatan}")
    public ResponseEntity<Void> delete(@PathVariable Long id_nama_jabatan) {
        service.deleteNamaJabatan(id_nama_jabatan);
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
