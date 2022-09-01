package net.draimcido.draimfishing.requirements.papi;

import java.util.HashMap;

public interface PapiRequirement {
    boolean isMet(HashMap<String, String> papiMap);
}
