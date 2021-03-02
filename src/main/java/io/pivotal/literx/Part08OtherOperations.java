package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Learn how to use various other operators.
 *
 * @author Sebastien Deleuze
 */
public class Part08OtherOperations {

//========================================================================================

    // TODO Create a Flux of user from Flux of username, firstname and lastname.
    Flux<User> userFluxFromStringFlux(Flux<String> usernameFlux, Flux<String> firstnameFlux, Flux<String> lastnameFlux) {
		Flux<Tuple2<Tuple2<String, String>, String>> tuple2Flux = usernameFlux.zipWith(firstnameFlux).zipWith(lastnameFlux);
		return tuple2Flux.map(tuple -> {
			Tuple2<String, String> t1 = tuple.getT1();
			String lastName = tuple.getT2();
			return new User(t1.getT1(), t1.getT2(), lastName);
		});
	}

//========================================================================================

    // TODO Return the mono which returns its value faster
    Mono<User> useFastestMono(Mono<User> mono1, Mono<User> mono2) {

        return mono1.or(mono2);
    }

//========================================================================================

    // TODO Return the flux which returns the first value faster
    Flux<User> useFastestFlux(Flux<User> flux1, Flux<User> flux2) {
        return flux1.or(flux2);
    }

//========================================================================================

    // TODO Convert the input Flux<User> to a Mono<Void> that represents the complete signal of the flux
    Mono<Void> fluxCompletion(Flux<User> flux) {
        return Mono.when(flux);
    }

//========================================================================================

    // TODO Return a valid Mono of user for null input and non null input user (hint: Reactive Streams do not accept null values)
    Mono<User> nullAwareUserToMono(User user) {
        return Mono.justOrEmpty(user);
    }

//========================================================================================

    // TODO Return the same mono passed as input parameter, expect that it will emit User.SKYLER when empty
    Mono<User> emptyToSkyler(Mono<User> mono) {
        return mono.switchIfEmpty(Mono.just(User.SKYLER));
    }

//========================================================================================

    // TODO Convert the input Flux<User> to a Mono<List<User>> containing list of collected flux values
    Mono<List<User>> fluxCollection(Flux<User> flux) {
		return flux.collect(Collectors.toList());
	}

}
