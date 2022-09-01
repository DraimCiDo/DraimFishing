package net.draimcido.draimfishing.requirements.papi;

import java.util.HashMap;

public record PapiSmaller(String papi, double requirement) implements PapiRequirement{

    @Override
    public boolean isMet(HashMap<String, String> papiMap) {
        double value = Double.parseDouble(papiMap.get(papi));
        return value < requirement;
    }
}
