package com.example.fc_plaza_service.domain.model;

public record Restaurant(
    String name, String nit, String address, String phone, String logoUrl, Long userId) {}
