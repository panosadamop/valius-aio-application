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

describe('BuyingCriteriaWeighting e2e test', () => {
  const buyingCriteriaWeightingPageUrl = '/buying-criteria-weighting';
  const buyingCriteriaWeightingPageUrlPattern = new RegExp('/buying-criteria-weighting(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const buyingCriteriaWeightingSample = { value: 'overriding', language: 'GREEK' };

  let buyingCriteriaWeighting;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/buying-criteria-weightings+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/buying-criteria-weightings').as('postEntityRequest');
    cy.intercept('DELETE', '/api/buying-criteria-weightings/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (buyingCriteriaWeighting) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/buying-criteria-weightings/${buyingCriteriaWeighting.id}`,
      }).then(() => {
        buyingCriteriaWeighting = undefined;
      });
    }
  });

  it('BuyingCriteriaWeightings menu should load BuyingCriteriaWeightings page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('buying-criteria-weighting');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BuyingCriteriaWeighting').should('exist');
    cy.url().should('match', buyingCriteriaWeightingPageUrlPattern);
  });

  describe('BuyingCriteriaWeighting page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(buyingCriteriaWeightingPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BuyingCriteriaWeighting page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/buying-criteria-weighting/new$'));
        cy.getEntityCreateUpdateHeading('BuyingCriteriaWeighting');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyingCriteriaWeightingPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/buying-criteria-weightings',
          body: buyingCriteriaWeightingSample,
        }).then(({ body }) => {
          buyingCriteriaWeighting = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/buying-criteria-weightings+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/buying-criteria-weightings?page=0&size=20>; rel="last",<http://localhost/api/buying-criteria-weightings?page=0&size=20>; rel="first"',
              },
              body: [buyingCriteriaWeighting],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(buyingCriteriaWeightingPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details BuyingCriteriaWeighting page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('buyingCriteriaWeighting');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyingCriteriaWeightingPageUrlPattern);
      });

      it('edit button click should load edit BuyingCriteriaWeighting page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BuyingCriteriaWeighting');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyingCriteriaWeightingPageUrlPattern);
      });

      it('edit button click should load edit BuyingCriteriaWeighting page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BuyingCriteriaWeighting');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyingCriteriaWeightingPageUrlPattern);
      });

      it('last delete button click should delete instance of BuyingCriteriaWeighting', () => {
        cy.intercept('GET', '/api/buying-criteria-weightings/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('buyingCriteriaWeighting').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyingCriteriaWeightingPageUrlPattern);

        buyingCriteriaWeighting = undefined;
      });
    });
  });

  describe('new BuyingCriteriaWeighting page', () => {
    beforeEach(() => {
      cy.visit(`${buyingCriteriaWeightingPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BuyingCriteriaWeighting');
    });

    it('should create an instance of BuyingCriteriaWeighting', () => {
      cy.get(`[data-cy="value"]`).type('Dynamic Dollar Cliffs').should('have.value', 'Dynamic Dollar Cliffs');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        buyingCriteriaWeighting = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', buyingCriteriaWeightingPageUrlPattern);
    });
  });
});
