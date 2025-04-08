package ru.kharevich.authenticationservice.utils.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.kharevich.authenticationservice.exceptions.PersonTypeConvertionException;
import ru.kharevich.authenticationservice.model.Person;

import static ru.kharevich.authenticationservice.utils.constants.AuthenticationServiceResponseConstants.PERSON_ENUM_CONVERTION_EXCEPTION;

@Converter
public class PersonConverter implements AttributeConverter<Person, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Person person) {
        if (person == null) {
            throw new PersonTypeConvertionException(PERSON_ENUM_CONVERTION_EXCEPTION);
        }
        return person.getCode();
    }

    @Override
    public Person convertToEntityAttribute(Integer code) {
        return Person.fromCode(code)
                .orElseThrow(() -> new PersonTypeConvertionException(PERSON_ENUM_CONVERTION_EXCEPTION));
    }

}
