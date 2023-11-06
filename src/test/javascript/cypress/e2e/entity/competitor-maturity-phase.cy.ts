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

describe('CompetitorMaturityPhase e2e test', () => {
  const competitorMaturityPhasePageUrl = '/competitor-maturity-phase';
  const competitorMaturityPhasePageUrlPattern = new RegExp('/competitor-maturity-phase(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const competitorMaturityPhaseSample = { value: 'deposit Baht circuit', language: 'GREEK' };

  let competitorMaturityPhase;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/competitor-maturity-phases+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/competitor-maturity-phases').as('postEntityRequest');
    cy.intercept('DELETE', '/api/competitor-maturity-phases/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (competitorMaturityPhase) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/competitor-maturity-phases/${competitorMaturityPhase.id}`,
      }).then(() => {
        competitorMaturityPhase = undefined;
      });
    }
  });

  it('CompetitorMaturityPhases menu should load CompetitorMaturityPhases page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('competitor-maturity-phase');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CompetitorMaturityPhase').should('exist');
    cy.url().should('match', competitorMaturityPhasePageUrlPattern);
  });

  describe('CompetitorMaturityPhase page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(competitorMaturityPhasePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CompetitorMaturityPhase page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/competitor-maturity-phase/new$'));
        cy.getEntityCreateUpdateHeading('CompetitorMaturityPhase');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitorMaturityPhasePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/competitor-maturity-phases',
          body: competitorMaturityPhaseSample,
        }).then(({ body }) => {
          competitorMaturityPhase = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/competitor-maturity-phases+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/competitor-maturity-phases?page=0&size=20>; rel="last",<http://localhost/api/competitor-maturity-phases?page=0&size=20>; rel="first"',
              },
              body: [competitorMaturityPhase],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(competitorMaturityPhasePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CompetitorMaturityPhase page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('competitorMaturityPhase');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitorMaturityPhasePageUrlPattern);
      });

      it('edit button click should load edit CompetitorMaturityPhase page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CompetitorMaturityPhase');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitorMaturityPhasePageUrlPattern);
      });

      it('edit button click should load edit CompetitorMaturityPhase page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CompetitorMaturityPhase');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitorMaturityPhasePageUrlPattern);
      });

      it('last delete button click should delete instance of CompetitorMaturityPhase', () => {
        cy.intercept('GET', '/api/competitor-maturity-phases/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('competitorMaturityPhase').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitorMaturityPhasePageUrlPattern);

        competitorMaturityPhase = undefined;
      });
    });
  });

  describe('new CompetitorMaturityPhase page', () => {
    beforeEach(() => {
      cy.visit(`${competitorMaturityPhasePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CompetitorMaturityPhase');
    });

    it('should create an instance of CompetitorMaturityPhase', () => {
      cy.get(`[data-cy="value"]`).type('Naira').should('have.value', 'Naira');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        competitorMaturityPhase = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', competitorMaturityPhasePageUrlPattern);
    });
  });
});
