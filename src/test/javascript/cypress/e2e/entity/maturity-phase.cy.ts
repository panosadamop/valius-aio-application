import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('MaturityPhase e2e test', () => {
  const maturityPhasePageUrl = '/maturity-phase';
  const maturityPhasePageUrlPattern = new RegExp('/maturity-phase(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const maturityPhaseSample = { value: 'Account one-to-one', language: 'GREEK' };

  let maturityPhase;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/maturity-phases+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/maturity-phases').as('postEntityRequest');
    cy.intercept('DELETE', '/api/maturity-phases/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (maturityPhase) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/maturity-phases/${maturityPhase.id}`,
      }).then(() => {
        maturityPhase = undefined;
      });
    }
  });

  it('MaturityPhases menu should load MaturityPhases page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('maturity-phase');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MaturityPhase').should('exist');
    cy.url().should('match', maturityPhasePageUrlPattern);
  });

  describe('MaturityPhase page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(maturityPhasePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MaturityPhase page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/maturity-phase/new$'));
        cy.getEntityCreateUpdateHeading('MaturityPhase');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', maturityPhasePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/maturity-phases',
          body: maturityPhaseSample,
        }).then(({ body }) => {
          maturityPhase = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/maturity-phases+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/maturity-phases?page=0&size=20>; rel="last",<http://localhost/api/maturity-phases?page=0&size=20>; rel="first"',
              },
              body: [maturityPhase],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(maturityPhasePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MaturityPhase page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('maturityPhase');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', maturityPhasePageUrlPattern);
      });

      it('edit button click should load edit MaturityPhase page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MaturityPhase');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', maturityPhasePageUrlPattern);
      });

      it('edit button click should load edit MaturityPhase page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MaturityPhase');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', maturityPhasePageUrlPattern);
      });

      it('last delete button click should delete instance of MaturityPhase', () => {
        cy.intercept('GET', '/api/maturity-phases/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('maturityPhase').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', maturityPhasePageUrlPattern);

        maturityPhase = undefined;
      });
    });
  });

  describe('new MaturityPhase page', () => {
    beforeEach(() => {
      cy.visit(`${maturityPhasePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MaturityPhase');
    });

    it('should create an instance of MaturityPhase', () => {
      cy.get(`[data-cy="value"]`).type('Shilling Concrete Toys').should('have.value', 'Shilling Concrete Toys');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        maturityPhase = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', maturityPhasePageUrlPattern);
    });
  });
});
