package com.pusbin.layanan.data_agregat;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

public class DataAgregatSpecification {

    public static Specification<DataAgregat> hasInstansi(Long idInstansi) {
        return (root, query, cb)
                -> idInstansi == null ? null : cb.equal(
                                root
                                        .get("instansi")
                                        .get("idInstansi"), idInstansi
                        );
    }

    public static Specification<DataAgregat> hasPokja(Long idPokja) {
        return (root, query, cb)
                -> idPokja == null ? null : cb.equal(
                                root
                                        .get("instansi")
                                        .get("pokja")
                                        .get("idPokja"), idPokja
                        );
    }

    public static Specification<DataAgregat> hasJenis_instansi(Long idJenisInstansi) {
        return (root, query, cb)
                -> idJenisInstansi == null ? null : cb.equal(
                                root
                                        .get("instansi")
                                        .get("jenisInstansi")
                                        .get("idJenisInstansi"), idJenisInstansi
                        );
    }

    public static Specification<DataAgregat> hasAsn(Long idAsn) {
        return (root, query, cb)
                -> idAsn == null ? null : cb.equal(
                                root
                                        .get("asn")
                                        .get("idAsn"), idAsn
                        );
    }

    public static Specification<DataAgregat> hasJenjang(Long idJenjang) {
        return (root, query, cb)
                -> idJenjang == null ? null : cb.equal(
                                root
                                        .get("jabatan")
                                        .get("jenjang")
                                        .get("idJenjang"), idJenjang
                        );
    }

    public static Specification<DataAgregat> hasJabatan(Long idJabatan) {
        return (root, query, cb)
                -> idJabatan == null ? null : cb.equal(
                                root
                                        .get("jabatan")
                                        .get("idJabatan"), idJabatan
                        );
    }

    public static Specification<DataAgregat> hasNama_jabatan(Long idNamaJabatan) {
        return (root, query, cb)
                -> idNamaJabatan == null ? null : cb.equal(
                                root
                                        .get("jabatan")
                                        .get("namaJabatan")
                                        .get("idNamaJabatan"), idNamaJabatan
                        );
    }

    public static Specification<DataAgregat> hasKategori_instansi(Long idKategoriInstansi) {
        return (root, query, cb)
                -> idKategoriInstansi == null ? null : cb.equal(
                                root
                                        .get("instansi")
                                        .get("kategoriInstansi")
                                        .get("idKategoriInstansi"), idKategoriInstansi
                        );
    }

    public static Specification<DataAgregat> hasNomenklatur(Long idNomenklatur) {
        return (root, query, cb)
                -> idNomenklatur == null ? null : cb.equal(
                                root
                                        .get("jabatan")
                                        .get("namaJabatan")
                                        .get("nomenklatur")
                                        .get("idNomenklatur"), idNomenklatur
                        );
    }

    public static Specification<DataAgregat> hasWilayah_kerja(Long idWilayah) {
        return (root, query, cb)
                -> idWilayah == null ? null : cb.equal(
                                root
                                        .get("instansi")
                                        .get("wilayahKerja")
                                        .get("idWilayah"), idWilayah
                        );
    }

    public static Specification<DataAgregat> hasMultiInstansi(List<Long> idInstansi) {
        return (root, query, cb)
                -> (idInstansi == null || idInstansi.isEmpty())
                ? null
                : root.get("instansi").get("idInstansi").in(idInstansi);
    }

    public static Specification<DataAgregat> hasMultiPokja(List<Long> idPokja) {
        return (root, query, cb)
                -> (idPokja == null || idPokja.isEmpty())
                ? null
                : root.get("instansi").get("pokja").get("idPokja").in(idPokja);
    }

    public static Specification<DataAgregat> hasMultiJenisInstansi(List<Long> idJenisInstansi) {
        return (root, query, cb)
                -> (idJenisInstansi == null || idJenisInstansi.isEmpty())
                ? null
                : root.get("instansi").get("jenisInstansi").get("idJenisInstansi").in(idJenisInstansi);
    }

    public static Specification<DataAgregat> hasMultiAsn(List<Long> idAsn) {
        return (root, query, cb)
                -> (idAsn == null || idAsn.isEmpty())
                ? null
                : root.get("asn").get("idAsn").in(idAsn);
    }

    public static Specification<DataAgregat> hasMultiJenjang(List<Long> idJenjang) {
        return (root, query, cb)
                -> (idJenjang == null || idJenjang.isEmpty())
                ? null
                : root.get("jabatan").get("jenjang").get("idJenjang").in(idJenjang);
    }

    public static Specification<DataAgregat> hasMultiJabatan(List<Long> idJabatan) {
        return (root, query, cb)
                -> (idJabatan == null || idJabatan.isEmpty())
                ? null
                : root.get("jabatan").get("idJabatan").in(idJabatan);
    }

    public static Specification<DataAgregat> hasMultiNamaJabatan(List<Long> idNamaJabatan) {
        return (root, query, cb)
                -> (idNamaJabatan == null || idNamaJabatan.isEmpty())
                ? null
                : root.get("jabatan").get("namaJabatan").get("idNamaJabatan").in(idNamaJabatan);
    }

    public static Specification<DataAgregat> hasMultiKategoriInstansi(List<Long> idKategoriInstansi) {
        return (root, query, cb)
                -> (idKategoriInstansi == null || idKategoriInstansi.isEmpty())
                ? null
                : root.get("instansi").get("kategoriInstansi").get("idKategoriInstansi").in(idKategoriInstansi);
    }

    public static Specification<DataAgregat> hasMultiNomenklatur(List<Long> idNomenklatur) {
        return (root, query, cb)
                -> (idNomenklatur == null || idNomenklatur.isEmpty())
                ? null
                : root.get("jabatan").get("namaJabatan").get("nomenklatur").get("idNomenklatur").in(idNomenklatur);
    }

    public static Specification<DataAgregat> hasMultiWilayahKerja(List<Long> idWilayah) {
        return (root, query, cb)
                -> (idWilayah == null || idWilayah.isEmpty())
                ? null
                : root.get("instansi").get("wilayahKerja").get("idWilayah").in(idWilayah);
    }

}
