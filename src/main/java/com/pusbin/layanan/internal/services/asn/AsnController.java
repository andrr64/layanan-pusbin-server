package com.pusbin.layanan.internal.services.asn;

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

import com.pusbin.layanan.internal.services.asn.dto.RequestTambahAsn;
import com.pusbin.layanan.internal.services.asn.dto.RequestUpdateAsn;
import com.pusbin.layanan.common.dto.ApiResponse;
import com.pusbin.layanan.common.dto.GetFilter;
import com.pusbin.layanan.internal.models.Asn;

@RestController
@RequestMapping("/api/v1/asn")
public class AsnController {
    private final AsnService service;

    public AsnController(AsnService service) {
        this.service = service;
    }

    //GET /ASN
    @GetMapping
    public List<Asn> getAll() {
        return service.getAllAsn();
    }

    //GET /Asn/{id}
    @GetMapping("/{id_asn}")
    public ResponseEntity<Asn> getById(@PathVariable Long id_asn) {
        return service.getAsnById(id_asn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST /asn
    @PostMapping()
    public Asn create(@RequestBody RequestTambahAsn body) {
        return service.createAsn(body.getJenis_asn());
    }

    //PUT /asn/{id}
    @PutMapping("/{id_asn}")
    public ResponseEntity<Asn> update(@PathVariable Long id_asn, @RequestBody RequestUpdateAsn body) {
        try {
            Asn updated = service.updateAsn(id_asn, body.getJenis_asn());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Delete /Asn/{id}
    @DeleteMapping("/{id_asn}")
    public ResponseEntity<Void> delete(@PathVariable Long id_asn) {
        service.deleteAsn(id_asn);
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
