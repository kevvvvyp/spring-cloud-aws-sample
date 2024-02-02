package io.github.kevvvvyp.springcloudawssample.transfer;

import jakarta.validation.constraints.NotBlank;

public record OriginEvent(@NotBlank String text) {}
