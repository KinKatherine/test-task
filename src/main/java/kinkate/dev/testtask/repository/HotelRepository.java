package kinkate.dev.testtask.repository;

import kinkate.dev.testtask.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {


    @Query(value = "SELECT DISTINCT h.* FROM hotels h " +
            "LEFT JOIN hotel_amenities a ON h.id = a.hotel_id " +
            "WHERE (:name IS NULL OR h.name = :name) " +
            "AND (:brand IS NULL OR h.brand = :brand) " +
            "AND (:city IS NULL OR h.city = :city) " +
            "AND (:country IS NULL OR h.country = :country) " +
            "AND (:amenity IS NULL OR a.amenity = :amenity)",
            nativeQuery = true)
    List<Hotel> searchHotels(
            @Param("name") String name,
            @Param("brand") String brand,
            @Param("city") String city,
            @Param("country") String country,
            @Param("amenity") String amenities);

    @Query(value = "SELECT brand, COUNT(id) FROM hotels GROUP BY brand", nativeQuery = true)
    List<Object[]> countByBrand();

    @Query(value = "SELECT city, COUNT(id) FROM hotels GROUP BY city", nativeQuery = true)
    List<Object[]> countByCity();

    @Query(value = "SELECT country, COUNT(id) FROM hotels GROUP BY country", nativeQuery = true)
    List<Object[]> countByCountry();

    @Query(value = "SELECT amenity, COUNT(hotel_id) FROM hotel_amenities GROUP BY amenity", nativeQuery = true)
    List<Object[]> countByAmenities();
}
