package ru.boris.naujava.dao.criteria;

import ru.boris.naujava.entity.AmbulanceTeam;
import java.util.List;

public interface AmbulanceTeamCriteriaRepository {
    List<AmbulanceTeam> findByTeamCode(String teamCode);
    List<AmbulanceTeam> findByShift(String shift);
}
