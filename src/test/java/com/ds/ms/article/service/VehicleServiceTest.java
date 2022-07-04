package com.ds.ms.article.service;

import com.ds.ms.article.repository.VehicleRepository;
import com.ds.ms.article.domain.Vehicle;
import com.ds.ms.article.domain.VehicleDto;
import com.ds.ms.article.domain.VehicleDtoWithId;
import com.ds.ms.article.exceptions.VehicleNotFoundException;
import com.ds.ms.article.mappers.VehicleMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VehicleServiceTest {
    @Autowired
    private VehicleService vehicleService;
    @MockBean
    private VehicleRepository vehicleRepository;
    @Autowired
    private VehicleMapper vehicleMapper;

    @Test
    @DisplayName("Should updates the vehicle when the vehicle exists")
    public void testUpdateVehicleWhenVehicleExists() {
        VehicleDtoWithId vehicleDtoWithId = new VehicleDtoWithId(1L, 4, "red", "ABC-123");
        Vehicle vehicle = new Vehicle(1L, 4, "red", "ABC-123");

        when(vehicleRepository.findById(vehicleDtoWithId.getId())).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(Mockito.any())).thenReturn(vehicle);

        VehicleDtoWithId updatedVehicle = vehicleService.updateVehicle(vehicleDtoWithId);

        assertEquals(vehicleDtoWithId, updatedVehicle);
    }

    @Test
    @DisplayName("Should throws an exception when the vehicle does not exist")
    public void testUpdateVehicleWhenVehicleDoesNotExistThenThrowsException() {
        VehicleDtoWithId vehicleDtoWithId = new VehicleDtoWithId(1L, 4, "red", "ABC-123");
        when(vehicleRepository.findById(vehicleDtoWithId.getId())).thenReturn(Optional.empty());

        assertThrows(
                VehicleNotFoundException.class,
                () -> {
                    vehicleService.updateVehicle(vehicleDtoWithId);
                });
    }

    @Test
    @DisplayName("Should returns all vehicles")
    public void testFindAllShouldReturnsAllVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(new Vehicle(1L, 4, "red", "ABC-123"));
        vehicleList.add(new Vehicle(2L, 4, "blue", "DEF-456"));
        when(vehicleRepository.findAll()).thenReturn(vehicleList);

        List<VehicleDtoWithId> vehicleDtoWithIdList = vehicleService.findAll();

        assertEquals(2, vehicleDtoWithIdList.size());
        assertEquals("red", vehicleDtoWithIdList.get(0).getColor());
        assertEquals("blue", vehicleDtoWithIdList.get(1).getColor());
    }

    @Test
    @DisplayName("Should returns the vehicle when the vehicle is found")
    public void testFindByIdWhenVehicleIsFoundThenReturnsVehicle() {
        Vehicle vehicle = new Vehicle(1L, 4, "red", "ABC-123");
        when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));

        VehicleDtoWithId vehicleDtoWithId = vehicleService.findById(vehicle.getId());

        assertEquals(vehicle.getId(), vehicleDtoWithId.getId());
        assertEquals(vehicle.getNbDoors(), vehicleDtoWithId.getNbDoors());
        assertEquals(vehicle.getColor(), vehicleDtoWithId.getColor());
        assertEquals(vehicle.getPlate(), vehicleDtoWithId.getPlate());
    }

    @Test
    @DisplayName("Should throws an exception when the vehicle is not found")
    public void testFindByIdWhenVehicleIsNotFoundThenThrowsException() {
        Long vehicleId = 1L;
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        assertThrows(
                VehicleNotFoundException.class,
                () -> {
                    vehicleService.findById(vehicleId);
                });
    }

    @Test
    @DisplayName("Should saves the vehicle")
    public void testSaveVehicle() {
        VehicleDto vehicleDto = new VehicleDto(4, "red", "ABC-123");
        Vehicle vehicle = new Vehicle(1L, 4, "red", "ABC-123");
        Vehicle vehicleInput = vehicleMapper.vehicleDtoToVehicle(vehicleDto);
        when(vehicleRepository.save(vehicleInput)).thenReturn(vehicle);

        VehicleDtoWithId vehicleDtoWithId = vehicleService.saveVehicle(vehicleDto);

        assertEquals(1L, vehicleDtoWithId.getId());
        assertEquals("red", vehicleDtoWithId.getColor());
        assertEquals("ABC-123", vehicleDtoWithId.getPlate());
    }

    @Test
    @DisplayName("Should returns the vehicle with id")
    public void testSaveVehicleReturnsTheVehicleWithId() {
        VehicleDto vehicleDto = new VehicleDto(4, "red", "ABC-123");
        Vehicle vehicle = new Vehicle(1L, 4, "red", "ABC-123");
        Vehicle vehicleInput = vehicleMapper.vehicleDtoToVehicle(vehicleDto);
        when(vehicleRepository.save(vehicleInput)).thenReturn(vehicle);
        VehicleDtoWithId vehicleDtoWithId = vehicleService.saveVehicle(vehicleDto);

        assertEquals(1L, vehicleDtoWithId.getId());
        assertEquals("red", vehicleDtoWithId.getColor());
        assertEquals("ABC-123", vehicleDtoWithId.getPlate());
    }

    @Test
    @DisplayName("Should returns the vehicle when the plate is found")
    public void testFindByPlateWhenPlateIsFoundThenReturnsVehicle() {
        String plate = "ABC-123";
        Vehicle vehicle = new Vehicle(1L, 4, "red", plate);
        when(vehicleRepository.findByPlate(plate)).thenReturn(Optional.of(vehicle));

        VehicleDtoWithId vehicleDtoWithId = vehicleService.findByPlate(plate);

        assertEquals(vehicle.getId(), vehicleDtoWithId.getId());
        assertEquals(vehicle.getNbDoors(), vehicleDtoWithId.getNbDoors());
        assertEquals(vehicle.getColor(), vehicleDtoWithId.getColor());
        assertEquals(vehicle.getPlate(), vehicleDtoWithId.getPlate());
    }

    @Test
    @DisplayName("Should throws an exception when the plate is not found")
    public void testFindByPlateWhenPlateIsNotFoundThenThrowsException() {
        String plate = "ABC-123";
        when(vehicleRepository.findByPlate(plate)).thenReturn(Optional.empty());

        assertThrows(
                VehicleNotFoundException.class,
                () -> {
                    vehicleService.findByPlate(plate);
                });
    }
}
