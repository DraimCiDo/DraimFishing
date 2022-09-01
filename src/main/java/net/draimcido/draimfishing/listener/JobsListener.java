package net.draimcido.draimfishing.listener;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.api.JobsExpGainEvent;
import com.gamingmesh.jobs.container.Job;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JobsListener implements Listener {

    @EventHandler
    public void onExpAdd(JobsExpGainEvent event){
        Job job = Jobs.getJob("Fisherman");
        if (event.getJob().equals(job)){
            event.setExp(0);
        }
    }
}
