package com.kigo.vehicles.model.dto;

import com.kigo.vehicles.model.spawners.ProbabilisticSpawnerParams;

public record HabitatParams(ProbabilisticSpawnerParams automobileParams, ProbabilisticSpawnerParams motorbikeParams) {
}
