package com.example.fc_plaza_service.infrastructure.entrypoint.dto;

public record DefaultServerResponse<T, E>(T data, E error) {}
