package com.atsho.activitimanage.dao;

import com.atsho.activitimanage.beans.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

}