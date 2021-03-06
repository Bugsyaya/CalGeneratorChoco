package solver.contraintes;

import models.common.ConstraintPriority;
import models.common.TrainingFrequency;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import solver.modelChoco.ModuleChoco;

import java.util.List;

public class ContrainteChocoPeriodeFormation extends ContrainteChoco<TrainingFrequency>  {


    public ContrainteChocoPeriodeFormation(Model model, ConstraintPriority<TrainingFrequency> contrainteMaxSemaineFormation, List<ModuleChoco> modulesInChoco) {
        super(model, contrainteMaxSemaineFormation, modulesInChoco);
    }

    @Override
    public Constraint createConstraint(ModuleChoco module) {
        return model.and( getModulesInChoco().stream().filter(m -> m != module).map( m ->
                    model.or(
                            model.arithm(
                                    module.getFin(),"-", m.getDebut(),
                                    "<", getContraintePriority().getValue().getMaxWeekInTraining() * 7 ),
                            model.arithm(
                                    module.getDebut(),"-", m.getFin(),
                                    ">", getContraintePriority().getValue().getMinWeekInCompany() * 7 + 1)
                    )).toArray(Constraint[]::new));

    }

    @Override
    public String getConstraintName()
    {
        return String.format("Fréquence de formation de %d semaines et de %d semaines en entreprise", getContraintePriority().getValue().getMaxWeekInTraining(), getContraintePriority().getValue().getMinWeekInCompany());
    }

}
