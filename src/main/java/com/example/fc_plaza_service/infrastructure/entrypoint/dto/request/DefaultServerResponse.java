package com.example.fc_plaza_service.infrastructure.entrypoint.dto.request;

public record DefaultServerResponse<T, E>(T data, E error) {}
