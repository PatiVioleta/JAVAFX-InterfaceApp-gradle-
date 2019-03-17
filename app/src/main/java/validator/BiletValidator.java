package validator;

import model.Bilet;

public class BiletValidator implements Validator<Bilet> {
    @Override
    public void validate(Bilet entity) throws ValidationException {
        String err = new String();

        if(entity.getNumeCumparator().equals(""))
            err=err+"Numele cumparatorului este invalid!\n";

        if(entity.getNrLocDorite()<=0)
            err=err+"Numarul de locuri dorite este invalid!\n";

        if(entity.getSpectacol().getNrLocDisp()<entity.getNrLocDorite())
            err=err+"Insuficiente locuri disponibile!\n";

        if(err.length()!=0)
            throw new ValidationException(err);
    }
}
