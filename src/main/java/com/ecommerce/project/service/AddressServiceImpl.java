package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDto;
import com.ecommerce.project.repositories.AddressRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public AddressDto createAddress(AddressDto addressDto, User user) {

        Address address = modelMapper.map(addressDto, Address.class);
        List<Address> addressList = user.getAddresses();

        addressList.add(address);
        user.setAddresses(addressList);

        address.setUser(user);
        Address savedAddress = addressRepository.save(address);

        return modelMapper.map(savedAddress, AddressDto.class);


    }

    @Override
    public List<AddressDto> getAddresses() {
        List<Address> addresses = addressRepository.findAll();

        return addresses.stream()
                .map(address ->
                    modelMapper.map(address, AddressDto.class)
                ).toList();
    }

    @Override
    public AddressDto getAddressById(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        AddressDto addressDto = modelMapper.map(address, AddressDto.class);
        return  addressDto;

    }

    @Override
    public List<AddressDto> getUserAddresses(User user) {


        List<Address> addresses = user.getAddresses();


        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList();
    }

    @Override
    public AddressDto updateAddress(Long addressId, AddressDto addressDto) {
        Address addressFromDatabase = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address","addressId", addressId));

        addressFromDatabase.setCity(addressDto.getCity());
        addressFromDatabase.setPincode(addressDto.getPincode());
        addressFromDatabase.setState(addressDto.getState());
        addressFromDatabase.setCountry(addressDto.getCountry());
        addressFromDatabase.setStreet(addressDto.getStreet());
        addressFromDatabase.setBuildingName(addressDto.getBuildingName());

        Address updatedAddress = addressRepository.save(addressFromDatabase);
        User user = addressFromDatabase.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddresses().add(updatedAddress);
        userRepository.save(user);

        return modelMapper.map(updatedAddress, AddressDto.class);
    }

    @Override
    public String deleteAddress(Long addressId) {

        Address addressFromDatabase = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address","addressId", addressId));


        User user = addressFromDatabase.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        userRepository.save(user);

        addressRepository.delete(addressFromDatabase);
        return "Address deleted sucesfully with addressId: " + addressId;
    }
}
