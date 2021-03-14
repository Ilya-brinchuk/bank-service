package bankservice.demo.service;

public interface MapperToDto<E, T> {
    T mapToDto(E entity);
}
