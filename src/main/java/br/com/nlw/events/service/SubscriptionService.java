package br.com.nlw.events.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nlw.events.dto.SubscriptionRankingByUser;
import br.com.nlw.events.dto.SubscriptionRankingItem;
import br.com.nlw.events.dto.SubscriptionResponse;
import br.com.nlw.events.exception.EventNotFoundException;
import br.com.nlw.events.exception.SubscriptionConflictException;
import br.com.nlw.events.exception.UserInviteeNotFound;
import br.com.nlw.events.model.Event;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;
import br.com.nlw.events.repo.EventRepo;
import br.com.nlw.events.repo.SubscriptionRepo;
import br.com.nlw.events.repo.UserRepo;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private SubscriptionRepo subscriptionRepo;

    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId) {
        Subscription subscription = new Subscription();
        Event evt = eventRepo.findByPrettyName(eventName);
        if (evt == null) {
            throw new EventNotFoundException("Event not found: " + eventName);
        }

        User existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser == null) {
            existingUser = userRepo.save(user);
        }

        User inviteeUser = null;
        if (userId != null){
            inviteeUser = userRepo.findById(userId).orElse(null);
            if (inviteeUser == null) {
                throw new UserInviteeNotFound("User not found: " + userId);
            }
        }

        subscription.setEvent(evt);
        subscription.setUser(existingUser);
        subscription.setIndicationUser(inviteeUser);

        Subscription tempSubscription = subscriptionRepo.findByEventAndUser(evt, existingUser);
        if (tempSubscription != null) {
            throw new SubscriptionConflictException("User " + existingUser.getName() + " already subscribed to this event: " + evt.getTitle());
        }

        Subscription result = subscriptionRepo.save(subscription);
        return new SubscriptionResponse(result.getSubscriptionNumber(), "https://www.portifolio.bernardomecabo.com.br/events/" + evt.getPrettyName() + "/subscription/" + result.getSubscriptionNumber());
    }

    public List<SubscriptionRankingItem> getCompleteRanking(String prettyName) {
        Event event = eventRepo.findByPrettyName(prettyName);
        if (event == null) {
            throw new EventNotFoundException("Event not found: " + prettyName);
        }
        return subscriptionRepo.generateRanking(event.getId());
    }

    public SubscriptionRankingByUser getRankingByUser(String prettyName, Integer userId) {
        Event event = eventRepo.findByPrettyName(prettyName);
        if (event == null) {
            throw new EventNotFoundException("Event not found: " + prettyName);
        }

        List<SubscriptionRankingItem> ranking = getCompleteRanking(prettyName);
        SubscriptionRankingItem item = ranking.stream()
                .filter(i -> i.userId()
                .equals(userId))
                .findFirst()
                .orElse(null);
                
        if (item == null) {
            throw new UserInviteeNotFound("User not found: " + userId);
        }

        Integer position = IntStream.range(0, ranking.size())
                .filter(pos -> ranking.get(pos).userId().equals(userId))
                .findFirst()
                .getAsInt();

        return new SubscriptionRankingByUser(item, position + 1);
    }
}
