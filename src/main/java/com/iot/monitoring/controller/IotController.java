package com.iot.monitoring.controller;

import java.util.ArrayList;
import java.util.List;

import org.jtransfo.JTransfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iot.monitoring.dao.DeDao;
import com.iot.monitoring.dto.DeDto;
import com.iot.monitoring.model.De;
import com.iot.monitoring.service.IotService;

@RestController
public class IotController {

	@Autowired
	DeDao deDao;

	@Autowired
	JTransfo jTransfo;

	@Autowired
	IotService iotService;
	

	
	@RequestMapping(value = "/updateValue", method = RequestMethod.POST)
	@CrossOrigin(origins = "*")

	public String updateValue(@RequestParam int id,@RequestParam int number1, @RequestParam int number2)
	{
		De de = new De(id, number1, number2, true, false);
		deDao.save(de);
	}
	
@RequestMapping(value = "/checkPartie", method = RequestMethod.POST)
@CrossOrigin(origins = "*")
public String check(@RequestParam int player)
{
	String resultat="";
		int i=0;
		boolean tousOk = true;
		boolean aTermine = false;
		List<De> des = deDao.findAll();
//		int pl = Integer.parseInt(player);
		while (tousOk && i<des.size()){
			if(des.get(i).isaJouer()){
				i++;
			}
			else{
			tousOk =false;
			resultat = "3,0,rien";
			}
			
		}
		if(tousOk){
		resultat = iotService.checkResultat(des, player);
		for(int cpt=0; cpt< des.size(); cpt++){
			if(des.get(cpt).getId()== player){
				des.get(cpt).setResultatRecup(true);
				deDao.save(des);
			}
		}
		if(des.get(0).isResultatRecup() && des.get(1).isResultatRecup()) {
			resetPartie();
			}
		}
		return resultat;
		
}

@RequestMapping(value = "/resetPartie", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public String resetPartie()
{
	List<De> des = deDao.findAll();
	for(int i=0; i<des.size(); i++)
	{
		des.get(i).setaJouer(false);
		des.get(i).setResultatRecup(false);
	}
	deDao.save(des);
	return "OK";
	
}

@RequestMapping(value = "/verifEnd", method = RequestMethod.POST)
@CrossOrigin(origins = "*")
public int verifEnd(int player) {
	List<De> des = deDao.findAll();
	int s =0;
	for(int i=0; i< des.size(); i++) {
		if(des.get(i).getId() == player) {
			if(des.get(i).isaJouer() && des.get(i).isResultatRecup()) {
				s = 0;
			} else {
				s = 1;
			}
		}
	}
	return s;
}
	
//	
//	@RequestMapping(value = "/check1", method = RequestMethod.POST)
//	@CrossOrigin(origins = "*")
//	public String check()
//	{
//		DeDto m = new DeDto();
//		De machine = (De) jTransfo.convert(m);
//		if(null != machine)
//		{
//			machineDao.save(machine);
//			return "OK";
//		}
//		
//		return "KO";
//		
//	}
//	
//	@RequestMapping(value = "/token{login}", method = RequestMethod.GET)
//	@CrossOrigin(origins = "*")
//	public DeDto informationList(@PathVariable String login)
//	{
//		DeDto machine = new DeDto();
//
//		return machine;
//	}
//	
//	@RequestMapping(value = "/updateSalle", method = RequestMethod.POST)
//	@CrossOrigin(origins = "*")
//	public String updateSalle(@RequestBody MachineSalleDto machineSalle)
//	{
//		
//		for(int i=0; i<machineSalle.getIdMachine().size();i++){
//			De m = machineDao.findByIdMachine(machineSalle.getIdMachine().get(i));
//			m.setIdSalle(machineSalle.getIdSalle());
//			machineDao.save(m);
//		}
//		return "ok";
//		
//	}
//	@RequestMapping(value = "/updateSalle2", method = RequestMethod.POST)	
//	@CrossOrigin(origins = "*")
//	public De updateSalle(@RequestBody DeDto machine)
//	{
//
//		De m = machineDao.findByIdMachine(machine.getIdMachine());
//		m.setIdSalle(machine.getIdSalle());
//		machineDao.save(m);
//		return m;
//		
//	}
//	
//	@RequestMapping(value = "/getMachinesBySalle", method = RequestMethod.GET)
//	@CrossOrigin(origins = "*")
//	public List<De> getMachinesBySalle(@RequestParam int idSalle)
//	{
//		return machineDao.findByIdSalle(idSalle);
//	}
	
//@RequestMapping(value = "/updatePlayer", method = RequestMethod.POST)	
//@CrossOrigin(origins = "*")
//public De updateSalle(@RequestBody DeDto machine)
//{
//
//	De m = machineDao.findByIdMachine(machine.getIdMachine());
//	m.setIdSalle(machine.getIdSalle());
//	machineDao.save(m);
//	return m;
//	
//}
	
	
	
}
