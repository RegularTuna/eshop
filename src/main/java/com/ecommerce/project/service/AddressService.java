package com.ecommerce.project.service;

import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto createAddress(AddressDto addressDto, User user);

    List<AddressDto> getAddresses();

    AddressDto getAddressById(Long addressId);

    List<AddressDto> getUserAddresses(User user);

    AddressDto updateAddress(Long addressId, AddressDto addressDto);

    String deleteAddress(Long addressId);
}
