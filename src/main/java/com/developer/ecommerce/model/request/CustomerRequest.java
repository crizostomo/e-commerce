package com.developer.ecommerce.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class CustomerRequest {

    @NotBlank
    @ApiModelProperty(value = "customer's name cannot be empty")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "customer's zip code cannot be empty")
    private String zipCode;

    @NotBlank
    @ApiModelProperty(value = "customer's email cannot be empty")
    private String email;

    @NotBlank
    @ApiModelProperty(value = "customer's age cannot be empty")
    private Integer age;

    @NotBlank
    @ApiModelProperty(value = "customer's status cannot be empty")
    private String status;

}
