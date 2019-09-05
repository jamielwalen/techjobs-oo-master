package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.launchcode.models.Job;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();
    public Integer jobID;
    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, Integer id) {

        // TODO #1 - get the Job with the given ID and pass it into the view

        Job someJob = jobData.findById(jobID);
        model.addAttribute("someJob", someJob);


        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "new-job";
        }else {
            String name = jobForm.getName();
            int empID = jobForm.getEmployerId();
            int locID = jobForm.getLocationId();
            int posID = jobForm.getPositionTypeId();
            int corID = jobForm.getCoreCompetencyId();


            Employer aEmployer = jobData.getEmployers().findById(empID);

            Location aLocation = jobData.getLocations().findById(locID);

            PositionType aPositionType = jobData.getPositionTypes().findById(posID);

            CoreCompetency aSkill = jobData.getCoreCompetencies().findById(corID);

            Job newJob = new Job(name, aEmployer, aLocation,
                    aPositionType, aSkill);

            jobData.add(newJob);

            ArrayList<Job> jobs = jobData.findAll();
            jobID = jobs.indexOf(newJob);
            jobID = jobID +1;

            return "redirect:/job";
        }





    }
}
