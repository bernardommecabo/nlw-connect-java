package br.com.nlw.events.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.nlw.events.dto.SubscriptionRankingItem;
import br.com.nlw.events.model.Event;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;

public interface SubscriptionRepo extends CrudRepository<Subscription, Integer> {
    public Subscription findByEventAndUser(Event event, User user);

    @Query(value = "SELECT count(subscription_number) as quantity, indication_user_id, user_name "
            + "FROM tbl_subscription inner join tbl_user "
            + "ON tbl_subscription.indication_user_id = tbl_user.user_id "
            + "WHERE indication_user_id IS NOT NULL "
            + "AND event_id = :eventId "
            + "GROUP BY indication_user_id "
            + "ORDER BY quantity DESC", nativeQuery = true)
    public List<SubscriptionRankingItem> generateRanking(@Param("eventId") Integer eventId); 
}
