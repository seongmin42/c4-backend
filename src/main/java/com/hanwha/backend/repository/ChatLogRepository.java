package com.hanwha.backend.repository;

import com.hanwha.backend.data.IData;
import com.hanwha.backend.data.dto.response.ChatRoomResponseDto;
import com.hanwha.backend.data.entity.ChatLog;
import com.hanwha.backend.data.enums.Role;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ChatLogRepository {
    private static final int NUMBER_OF_RECENT_CHAT_HISTORY = 10;
    private final MongoTemplate mongoTemplate;

    public ChatLogRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void insert(ChatLog chatLog) {
        mongoTemplate.insert(chatLog, "chatlog");
    }

    public void insert(IData data){
        mongoTemplate.insert((ChatLog) data, "chatlog");
    }
    public void insertAll(List<ChatLog> chatLogs){
        mongoTemplate.insertAll(chatLogs);
    }

    public List<ChatRoomResponseDto> findDistinctRoomByUserId(String userId){
        AggregationOperation matchByUserId = Aggregation.match(Criteria.where("user").is(userId));
        AggregationOperation matchByIsDeleted = Aggregation.match(Criteria.where("isDeleted").is(false));
        AggregationOperation groupByRoomId = Aggregation.group("roomId").push("content").as("contents").first("createdAt").as("createdAt");
        AggregationOperation orderByCreatedAt = Aggregation.sort(Sort.Direction.DESC, "createdAt");
        Aggregation aggregation = Aggregation.newAggregation(matchByUserId, matchByIsDeleted, groupByRoomId, orderByCreatedAt);
        List<ChatRoomResponseDto> result = mongoTemplate.aggregate(aggregation, "chatlog", ChatRoomResponseDto.class).getMappedResults();
        for(ChatRoomResponseDto e : result){
            if(e.getContents().size()>1){
                e.setContents(Collections.singletonList(e.getContents().get(1)));
            }
        }
        return result;
    }

    public List<ChatLog> findRecentChatLogByRoomId(String roomId){
        Query query = new Query();
        query.addCriteria(Criteria.where("roomId").is(roomId)).limit(NUMBER_OF_RECENT_CHAT_HISTORY).with(Sort.by(Sort.Direction.ASC, "createdAt"));
        query.addCriteria(Criteria.where("isDeleted").is(false));
        query.addCriteria(Criteria.where("role").ne(Role.system.toString()));
        return mongoTemplate.find(query, ChatLog.class);
    }

    public List<ChatLog> findChatLogByRoomId(String roomId){
        Query query = new Query();
        query.addCriteria(Criteria.where("roomId").is(roomId)).with(Sort.by(Sort.Direction.ASC, "createdAt"));
        query.addCriteria(Criteria.where("isDeleted").is(false));
        query.addCriteria(Criteria.where("role").ne(Role.system.toString()));
        return mongoTemplate.find(query, ChatLog.class);
    }

    public void deleteChatLogByRoomId(String roomId){
        Query query = new Query();
        query.addCriteria(Criteria.where("roomId").is(roomId));
        Update update = new Update();
        update.set("isDeleted", true);
        mongoTemplate.updateMulti(query, update, ChatLog.class);
    }

}
