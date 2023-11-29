package ma.projet.domaine;


import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import ma.projet.beans.Employe;
import ma.projet.beans.Service;
import ma.projet.service.EmployeService;
import ma.projet.service.ServiceService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean(name = "serviceBean")
public class ServiceBean {
    
    private Service service;
    private List<Service> services;
    private ServiceService serviceService;
    private EmployeService employeService;

    private static ChartModel barModel;
private List<Employe> emp;
    public ServiceBean() {
        service = new Service();
        serviceService = new ServiceService();
        employeService=new EmployeService();
        emp=new ArrayList<>();
    }

    public List<Service> getServices() {
        if (services == null) {
            services = serviceService.getAll();
            System.out.println(services);
        }
        return services;
    }

    public List<Employe> getEmp() {
        if(emp==null)
            emp=employeService.getAll();
        return emp;
    }

    public EmployeService getEmployeService() {
        return employeService;
    }

    public void setEmployeService(EmployeService employeService) {
        this.employeService = employeService;
    }

    public void setEmp(List<Employe> emp) {
        this.emp = emp;
    }

    public ServiceService getServiceService() {
        return serviceService;
    }

    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String onCreateAction() {
        serviceService.create(service);
        service = new Service();
        return null;
    }

    public String onDeleteAction() {
       // service.setEmployes(null);
        serviceService.delete(serviceService.getById(service.getId()));
        return null;
    }

    public void onEdit(RowEditEvent event) {
        service = (Service) event.getObject();
        //service.setEmployes(null);
        serviceService.update(service);
    }

    public void onCancel(RowEditEvent event) {
        // If needed, you can perform additional logic here.
    }

    public ChartModel getBarModel() {
        return barModel;
    }

    public ChartModel initBarModel() {
        CartesianChartModel model = new CartesianChartModel();
        ChartSeries services = new ChartSeries();
        services.setLabel("services");
        model.setAnimate(true);
        // Add chart series data if needed.
        return model;
    }
    public List<Employe> load(){
        for(Employe e:employeService.getAll())
            if(e.getService().getId()==service.getId())
                emp.add(e);
        System.out.print("visited");
        System.out.print(emp);
        return emp;
    }
}
