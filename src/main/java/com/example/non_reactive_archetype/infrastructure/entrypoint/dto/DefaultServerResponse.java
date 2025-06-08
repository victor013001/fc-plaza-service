package com.example.non_reactive_archetype.infrastructure.entrypoint.dto;

public record DefaultServerResponse<T, E>(T data, E error) {}
