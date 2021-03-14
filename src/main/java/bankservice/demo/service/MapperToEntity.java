package bankservice.demo.service;

public interface MapperToEntity<E, T> {
    E mapToEntity(T requestDto);
}
