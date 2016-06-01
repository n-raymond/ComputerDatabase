package com.excilys.mapper;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;

import com.excilys.dto.DTOComputer;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public class DTOComputerToComputer implements Converter<DTOComputer, Computer> {

    @Override
    public Computer convert(DTOComputer dto) {
        Computer.Builder builder = new Computer.Builder(dto.getName());
        if (!dto.getId().equals("")) {
            builder.id(Long.parseLong(dto.getId()));
        }
        if (!dto.getIntroduced().equals("")) {
            builder.introduced(LocalDate.parse(dto.getIntroduced()));
        }
        if (!dto.getDiscontinued().equals("")) {
            builder.discontinued(LocalDate.parse(dto.getDiscontinued()));
        }
        if (!dto.getCompanyId().equals("") && !dto.getCompanyName().equals("")) {
            builder.company(new Company(Long.parseLong(dto.getCompanyId()), dto.getCompanyName()));
        }
        return builder.build();
    }

}
