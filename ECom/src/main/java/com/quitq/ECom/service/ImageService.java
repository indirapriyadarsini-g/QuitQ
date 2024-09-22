package com.quitq.ECom.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	 public Image addImage(MultipartFile image,int pid,String usernme) throws InvalidIdException
	 {
		 List<Product> product=productRepository.findByVendorUsrname(usernme);
		List<Product> stream=product.stream().filter(p->p.getId()==pid).toList();
		 if(stream.isEmpty())
		 {
			 throw new InvalidIdException("Product doesnt exist for you");
		 }
		 Image img=new Image();
		 for(Product p:stream)
		 {
			 img.setP(p);
		 }
		 String fileName=image.getOriginalFilename();
		 img.setImageName(fileName);
		 
		 FileOutputStream fos;
		try {
			fos = new FileOutputStream("C:\\Users\\HUDA\\git\\repository_Ecom\\ECom\\src\\main\\resources\\static\\images\\" + fileName);
			InputStream is= image.getInputStream();
            byte[] byt=new byte[is.available()];
            is.read(byt);
            fos.write(byt);
        
            fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         //save this in the DB
 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		 
		 	return imageRepository.save(img);
	 }
	 public List<Image> getAllImageOfProduct(int pid,String username) throws InvalidIdException
	 {
		 List<Image> image=imageRepository.findImageByUsername(username);
			List<Image> streamList=image.stream().filter(i->i.getP().getId()==pid).toList();
			if(streamList.isEmpty())
			{
				throw new InvalidIdException("No such image exist for you");
			}

		return streamList;
	 }
	 public Image getSpecificImageOfProduct(int imageId,String username) throws InvalidIdException
	 {
		 List<Image> image=imageRepository.findImageByUsername(username);
		List<Image> streamList=image.stream().filter(i->i.getId()==imageId).toList();
		
for(Image i:streamList)
{
	return i;
}
throw new InvalidIdException("No such image exist for you");

	 }	 
public void deleteAllImageOfProduct(int pid,String username) throws InvalidIdException
{
	 List<Image> image=imageRepository.findImageByUsername(username);
		List<Image> streamList=image.stream().filter(i->i.getP().getId()==pid).toList();
		if(streamList.isEmpty())
		{
			throw new InvalidIdException("No such image exist for you");
		}
		imageRepository.deleteAll(streamList);
	
}

public void deleteSpecificImageOfProduct(int imageId,String username) throws InvalidIdException
{
	 List<Image> image=imageRepository.findImageByUsername(username);
		List<Image> streamList=image.stream().filter(i->i.getId()==imageId).toList();
		if(streamList.isEmpty())
		{
			throw new InvalidIdException("No such image exist for you");

		}
for(Image i:streamList)
{
	imageRepository.deleteById(i.getId());
	
}
}
public Image giveCoverImage(String username,int pid) throws InvalidIdException
{
	 List<Image> image=imageRepository.findImageByUsername(username);
		List<Image> streamList=image.stream().filter(i->i.getP().getId()==pid).toList();
		if(streamList.isEmpty())
		{
			throw new InvalidIdException("No such image exist for you");
		}
	Optional<Image> optionalImage=imageRepository.getImageWithProductIdAndCover(pid,"cover");
	if(optionalImage.isEmpty())
	{
		throw new InvalidIdException("No cover image for this product");
		
	}
	return optionalImage.get();
			
}
public void updateImage(int id, String userName) throws InvalidIdException {
	// TODO Auto-generated method stub
	List<Image> image=imageRepository.findImageByUsername(userName);
	List<Image> streamList=image.stream().filter(i->i.getId()==id).toList();
	if(streamList.isEmpty())
	{
		throw new InvalidIdException("No such image exist for you");
	}
	for(Image i:streamList)
	{
		Optional<Image> optionalImage=imageRepository.getImageWithProductIdAndCover(i.getP().getId(),"cover");
		if(!optionalImage.isEmpty())
		{
			Image img=optionalImage.get();
			img.setStatus("uncover");
		}
		i.setStatus("cover");
		imageRepository.save(i);
		
		
	}
	
}


}



