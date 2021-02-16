package com.example.MVCSpringBoot.Service.implement;

import com.example.MVCSpringBoot.Exceptions.ResourceNotFoundException;
import com.example.MVCSpringBoot.Model.Book;
import com.example.MVCSpringBoot.Model.Publisher;
import com.example.MVCSpringBoot.Repository.PublisherRepository;
import com.example.MVCSpringBoot.Service.PublisherService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("unchecked")
@NoArgsConstructor
public class PublisherServiceImpl extends PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public Page<Publisher> getAll(Pageable pageable )
    {
        return publisherRepository.findAll( pageable );
    }

    @Override
    public Publisher add( Publisher o )
    {
        return publisherRepository.save( o );
    }

    @Override
    public Publisher update( Publisher o, int id )
    {
        Publisher publisher = checkIfIdIsPresentandReturnPublisher( id );
        publisher.setName( o.getName() );
        publisher.setAddress( o.getAddress() );
        return publisherRepository.save( publisher );
    }

    @Override
    public Publisher getById( int id ) throws ResourceNotFoundException
    {
        return checkIfIdIsPresentandReturnPublisher( id );
    }

    @Override
    public Publisher deleteById( int id )
    {
        Publisher publisher = checkIfIdIsPresentandReturnPublisher( id );
        publisherRepository.deleteById( id );
        return publisher;
    }

    @Override
    public List<Book> getBookById(int id) {
        return checkIfIdIsPresentandReturnPublisher( id ).getBookList();
    }

    private Publisher checkIfIdIsPresentandReturnPublisher( int id )
    {
        if ( !publisherRepository.findById( id ).isPresent() )
            throw new ResourceNotFoundException( " Publisher id = " + id + " not found" );
        else
            return publisherRepository.findById( id ).get();
    }


}
