package sgsits.cse.dis.user.dtos;

import java.util.List;

public class ParticipantDto {
    private List<Object> participant;

    private String participantType;

    public List<Object> getParticipant() {
        return participant;
    }

    public void setParticipant(List<Object> participant) {

        this.participant = participant;
    }

    public String getParticipantType() {
        return participantType;
    }

    public void setParticipantType(String participantType) {
        this.participantType = participantType;
    }

}
