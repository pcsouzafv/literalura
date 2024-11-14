package services;

public interface IDataConverter{
	<T> T getData(String json, Class<T> classe);
}