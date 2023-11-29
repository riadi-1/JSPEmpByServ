/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.domaine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import ma.projet.beans.Employe;
import ma.projet.beans.Service;
import ma.projet.service.EmployeService;
import ma.projet.service.ServiceService;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
/**
 *
 * @author Ouissal
 */
@ManagedBean(name = "employeBean")
public class EmployeBean {
     private Employe employe;
     private Service service;
     private Employe chef;
    private EmployeService employeService;
    private List<Service> services;
    private List<Employe> employes;
    private List<Employe> collaborateurs;
    private List<Employe> chefs;
    private ServiceService serviceService;
    private static ChartModel barModel;
    private int SelectedService;
    private String photo;
    public EmployeBean() {
    employe = new Employe();
    employeService = new EmployeService();
    service=new Service();
    serviceService = new ServiceService();
    chef=new Employe();
    services = new ArrayList<>();
        collaborateurs = new ArrayList<>();

    employes = employeService.getAll();
    chefs = employeService.getAll();  
    }



     public List<Service> getServices() {
        if(services==null)
            services=serviceService.getAll();
          else {
        services.clear(); // Clear the list before adding elements
        services.addAll(serviceService.getAll());
    }
        return services;
    }

    public EmployeService getEmployeService() {
        return employeService;
    }

    public void setEmployeService(EmployeService employeService) {
        this.employeService = employeService;
    }

    public List<Employe> getCollaborateurs() {
        return collaborateurs;
    }

    public void setCollaborateurs(List<Employe> collaborateurs) {
        this.collaborateurs = collaborateurs;
    }

    public ServiceService getServiceService() {
        return serviceService;
    }

    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

  
    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Employe> getEmployes() {
        if(employes==null)
            employes=employeService.getAll();
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public List<Employe> getChefs() {
        if(chefs==null){
            chefs=employeService.getAll();
        }
        return chefs;
    }

    public void setChefs(List<Employe> chefs) {
        this.chefs = chefs;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Employe getChef() {
        return chef;
    }

    public void setChef(Employe chef) {
        this.chef = chef;
    }
            
        public String onCreateAction() {
        System.out.println("onCreateAction invoked");
        employe.setChef(chef);
        employe.setService(service);
        employe.setPhoto(null);
        employeService.create(employe);
        employe  = new Employe();
        return null;
    }     
        public void onDeleteAction() {
        System.out.println("onDeleteAction invoked");
        employe.setService(null);
        employe.setChef(null);
        employeService.delete(employe);

    }  
         public void onEdit(RowEditEvent event) {
             employe=(Employe)event.getObject();
         service = serviceService.getById(service.getId());
        employe.setService(service);
        if(chef.getId()!=employe.getId())
           employe.setChef(chef);
        employe.getService().setNom(service.getNom());
        employeService.update(employe);
    }
      public void onCancel(RowEditEvent event) {
    }
          public ChartModel getBarModel() {
        return barModel;
    }
public List<Employe> loadCollaborateur(){
    collaborateurs=new ArrayList<Employe>();
    if(chef!=null){
        System.out.print("chef info");
        System.out.print(chef.getId());
        for( Employe e:employeService.getAll())
        {
            System.out.print(e.getChef());
            if(e.getChef()!=null && e.getChef()!=chef)
            if(chef.getId()==e.getChef().getId())
                  collaborateurs.add(e);

        }
    System.out.print("visited load");
    System.out.print(collaborateurs);
    return collaborateurs;
    }
    else
    {
        System.out.print("erruer");
        return null;
    }
}
    public PieChartModel initPieModel() {
     PieChartModel pieChartModel = new PieChartModel();
pieChartModel.setDataFormat("value");

  for (Object[] m : employeService.nbEmployes()) {
           pieChartModel.set(m[1].toString(), Integer.parseInt(m[0].toString()));
        }
      pieChartModel.setShowDataLabels(true);

// Check the contents of the ChartSeries
    return pieChartModel;
}
public void handleFileUpload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        System.out.print("visited upload");

        // Define the directory where you want to store the uploaded files
        String uploadDirectory = "C:\\Users\\Ouissal\\Desktop\\images";

        // Create a unique file name to avoid overwriting existing files
        String fileName = FilenameUtils.getBaseName(uploadedFile.getFileName()) + "_"
                + System.currentTimeMillis() + "." + FilenameUtils.getExtension(uploadedFile.getFileName());

        try (InputStream input = uploadedFile.getInputstream()) {
            // Use Java NIO for file copy
            Path targetPath = new File(uploadDirectory, fileName).toPath();
            Files.copy(input, targetPath, StandardCopyOption.REPLACE_EXISTING);
            photo=uploadDirectory + fileName;
           /* employe.setPhoto(photo);
            employeService.create(employe);*/
        } catch (IOException e) {
            System.out.print("upload exception");
            e.printStackTrace();
            // Handle the exception (log it, show a message to the user, etc.)
        }
       //employeService.create(employe);
       System.out.print("CREATED EMPLOYE");
        // Display a message indicating the successful upload
        FacesMessage message = new FacesMessage("Successful",
                event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
