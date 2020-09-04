package game;

import game.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{

    @Autowired
    MissionRepository missionRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverAllocated_UpdateRewardId(@Payload Allocated allocated){

        if(allocated.isMe()){
            Optional<Mission> missionOptional = missionRepository.findById(allocated.getMissionId());
            Mission mission = missionOptional.get();
            mission.setRewardId(allocated.getId());
            mission.setStatus(allocated.getStatus());

            missionRepository.save(mission);

            System.out.println("##### listener RewardId : " + allocated.toJson());
        }
    }

}
