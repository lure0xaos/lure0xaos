package gargoyle.l0x.services.app.admin.base;

import gargoyle.l0x.model.app.base.*;
import gargoyle.l0x.entities.app.base.*;
import gargoyle.l0x.entities.user.User;

import java.util.function.Function;
import java.util.function.Supplier;

import static gargoyle.l0x.util.Aliases.toAlias;

public enum BaseAdminServiceUtil {
    ;

    public static void updateSourceEntity(SourceEntity destination, SourceModel source,
                                          Function<? super String, String> function) {
        destination.setSource(source.getSource());
        destination.setHead(source.getHead());
        destination.setBody(function.apply(source.getSource()));
    }

    public static void updateBiSourceEntity(BiSourceEntity destination, BiSourceModel source,
                                            Function<? super String, String> function) {
        destination.setPrimarySource(source.getPrimarySource());
        destination.setSecondarySource(source.getSecondarySource());
        destination.setHead(source.getHead());
        destination.setPrimaryBody(function.apply(source.getPrimarySource()));
        destination.setSecondaryBody(function.apply(source.getSecondarySource()));
    }

    public static void updateOwnerEntity(OwnerEntity destination, OwnerModel source, Supplier<? extends User> supplier) {
        destination.setAuthor(supplier.get());
    }

    public static void updateDatedEntity(DatedEntity destination, DatedModel source) {
        destination.setDate(source.getDate());
    }

    public static void updateAliasedEntity(AliasedEntity destination, AliasedModel source) {
        destination.setAlias(toAlias(source.getHead()));
    }
}
