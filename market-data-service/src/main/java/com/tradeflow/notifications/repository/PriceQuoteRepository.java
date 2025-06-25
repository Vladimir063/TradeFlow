package com.tradeflow.notifications.repository;


import com.tradeflow.notifications.model.PriceQuote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PriceQuoteRepository extends CrudRepository<PriceQuote, UUID > {


}

