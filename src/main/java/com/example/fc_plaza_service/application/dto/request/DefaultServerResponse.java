package com.example.fc_plaza_service.application.dto.request;

public record DefaultServerResponse<T, E>(T data, E error) {}
