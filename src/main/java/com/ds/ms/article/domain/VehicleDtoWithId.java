package com.ds.ms.article.domain;import lombok.AllArgsConstructor;import lombok.Data;import lombok.NoArgsConstructor;import java.io.Serializable;@Data@AllArgsConstructor@NoArgsConstructorpublic class VehicleDtoWithId implements Serializable {    private Long id;    private int nbDoors;    private String color;    private String plate;}