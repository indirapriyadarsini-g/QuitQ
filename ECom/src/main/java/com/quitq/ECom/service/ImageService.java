package com.quitq.ECom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quitq.ECom.config.Exception.InvalidIdException;
import com.quitq.ECom.model.Image;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.ImageRepository;
import com.quitq.ECom.repository.ProductRepository;

@Service
public class ImageService {
	 @Autowired
	    private ImageRepository imageRepository;
	 @Autowired
	 private ProductRepository productRepository;
	 /*

	    public ImageUploadResponse uploadImage(MultipartFile file) throws IOException {

	        imageDataRepository.save(Image.builder()
	                .name(file.getOriginalFilename())
	                .type(file.getContentType())
	                .imageData(ImageUtil.compressImage(file.getBytes())).build());

	        return new ImageUploadResponse("Image uploaded successfully: " +
	                file.getOriginalFilename());

	    }

	    @Transactional
	    public Image getInfoByImageByName(String name) {
	        Optional<Image> dbImage = imageDataRepository.findByImageName(name);

	        return Image.builder()
	                .name(dbImage.get().getImageName())
	                .type(dbImage.get().getType())
	                .imageData(ImageUtil.decompressImage(dbImage.get().getImageData())).build();

	    }

	    @Transactional
	    public byte[] getImage(String name) {
	        Optional<Image> dbImage = imageDataRepository.findByImageName(name);
	        byte[] image = ImageUtil.decompressImage(dbImage.get().getImageData());
	        return image;
	    }
	    */
	 public Image addImage(Image image,int pid,Vendor v) throws InvalidIdException
	 {
		 Optional<Product> optional=productRepository.findById(pid);
		 try {
			if(optional.isEmpty())
			 {
				 throw new InvalidIdException("Product not available");
			 }
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 if(v.getId()!=optional.get().getV().getId())
		 {
			 throw new InvalidIdException("You cannot add image to this product");
		 }
		 	image.setP(optional.get());
		 	if(image.getStatus().equals("cover"))
		 	{
		 		Optional<Image> optionalImage=imageRepository.getImageWithProductIdAndCover(pid,"cover");
		 		if(optionalImage.isEmpty())
		 		{
		 			image.setStatus("cover");
		 		}
		 		else
		 		{
		 			Image otherImage=optionalImage.get();
		 			otherImage.setStatus("uncover");
		 			imageRepository.save(otherImage);
		 			image.setStatus("cover");
		 			
		 			
		 			
		 		}
		 	}
		 
		 	return imageRepository.save(image);
	 }
	 public List<Image> getAllImageOfProduct(int pid,Vendor v) throws InvalidIdException
	 {
		 Optional<Product> optionalProduct=productRepository.findById(pid);
		 if(optionalProduct.isEmpty())
		 {
			throw new InvalidIdException("Product with this id not available");
			
		 }
		 Product p=optionalProduct.get();
		 if(v.getId()==p.getV().getId())
		 {
			 List<Image> images=imageRepository.getAllImageOfProduct(pid);
			 if(images.isEmpty())
			 {
				 throw new InvalidIdException("No image associated with this product");
			 }
			 return images;
		 }
		 throw new InvalidIdException("You cannot access this product");
	 }
	 public Image getSpecificImageOfProduct(int imageId,Vendor v) throws InvalidIdException
	 {
		 Optional<Image> optionalImage=imageRepository.findById(imageId);
		 if(optionalImage.isEmpty())
		 {
			 throw new InvalidIdException("not a valid image");
		 }
		 Image image=optionalImage.get();
		 if(v.getId()==image.getP().getV().getId())
		 {
			 return image;
		 }
		 throw new InvalidIdException("You cannot access this image");
		 
	 }
	 
public void deleteAllImageOfProduct(int pid,Vendor v) throws InvalidIdException
{
	
	 Optional<Product> optionalProduct=productRepository.findById(pid);
	 if(optionalProduct.isEmpty())
	 {
		throw new InvalidIdException("Product with this id not available");
		
	 }
	 Product p=optionalProduct.get();
	 if(v.getId()==p.getV().getId())
	 {
		 List<Image> images=imageRepository.getAllImageOfProduct(pid);
		 if(images.isEmpty())
		 {
			 throw new InvalidIdException("No image associated with this product");
		 }
		 for(Image img:images)
		 {
			 imageRepository.deleteById(img.getId());
		 }
	 }
	 else
	 {
		 throw new InvalidIdException("You cannot access this product");

	 }
}

public void deleteSpecificImageOfProduct(int imageId,Vendor v) throws InvalidIdException
{
	 Optional<Image> optionalImage=imageRepository.findById(imageId);
	 if(optionalImage.isEmpty())
	 {
		throw new InvalidIdException("Image with this id not available");
		
	 }
	 Product p=optionalImage.get().getP();
	 if(v.getId()==p.getV().getId())
	 {
		 
	 
			 imageRepository.deleteById(imageId);
		 
	 }
	 else
	 {
		 throw new InvalidIdException("You cannot access this product");

	 }
}
public void updateImage(Image newImage,int imageId,Vendor v) throws InvalidIdException
{
	Optional<Image> optionalImage=imageRepository.findById(imageId);
	if(optionalImage.isEmpty())
	{
		 throw new InvalidIdException("No image found");

	}
	Image image=optionalImage.get();
	if(v.getId()!=image.getP().getV().getId())
	{
		throw new InvalidIdException("You cannot access this product");
	}
	image.setImageData(newImage.getImageData());
	image.setImageName(newImage.getImageName());
	image.setType(newImage.getType());
	if(image.getStatus().equals("uncover"))
	{
		if(newImage.getStatus().equals("cover"))
		{
			Optional<Image> imageCover=imageRepository.getImageWithProductIdAndCover(image.getP().getId(),"cover");
			if(imageCover.isEmpty())
			{
				image.setStatus(newImage.getStatus());

			}
			else
			{
				throw new InvalidIdException("There is already one cover image uncover it first");
			}
		}
	}
	image.setStatus(newImage.getStatus());
	imageRepository.save(image);
	
	
}
public Image giveCoverImage(Vendor v,int pid) throws InvalidIdException
{
	Optional<Image> optionalImage=imageRepository.getImageWithProductIdAndCover(pid,"cover");
	if(optionalImage.isEmpty())
	{
		throw new InvalidIdException("No cover image for this product");
		
	}
	return optionalImage.get();
			
}


}



