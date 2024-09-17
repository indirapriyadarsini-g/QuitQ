package com.quitq.ECom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quitq.ECom.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image,Integer> {
	Optional<Image> findByImageName(String name);
@Query("select i  from Image i where i.status=?1")
	Optional<Image> findCoverImage(String string);
@Query("select i from Image i join i.p product where product.id=?1 order by i.status,i.id")
List<Image> getAllImageOfProduct(int pid);
@Query("select i from Image i join i.p product where product.id=?1 and i.status=?2")
Optional<Image> getImageWithProductIdAndCover(int id, String string);

}
