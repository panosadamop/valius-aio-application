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

describe('BuyingCriteria e2e test', () => {
  const buyingCriteriaPageUrl = '/buying-criteria';
  const buyingCriteriaPageUrlPattern = new RegExp('/buying-criteria(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const buyingCriteriaSample = { value: 'Berkshire structure Bedfordshire', language: 'ENGLISH' };

  let buyingCriteria;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/buying-criteria+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/buying-criteria').as('postEntityRequest');
    cy.intercept('DELETE', '/api/buying-criteria/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (buyingCriteria) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/buying-criteria/${buyingCriteria.id}`,
      }).then(() => {
        buyingCriteria = undefined;
      });
    }
  });

  it('BuyingCriteria menu should load BuyingCriteria page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('buying-criteria');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BuyingCriteria').should('exist');
    cy.url().should('match', buyingCriteriaPageUrlPattern);
  });

  describe('BuyingCriteria page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(buyingCriteriaPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BuyingCriteria page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/buying-criteria/new$'));
        cy.getEntityCreateUpdateHeading('BuyingCriteria');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyingCriteriaPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/buying-criteria',
          body: buyingCriteriaSample,
        }).then(({ body }) => {
          buyingCriteria = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/buying-criteria+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/buying-criteria?page=0&size=20>; rel="last",<http://localhost/api/buying-criteria?page=0&size=20>; rel="first"',
              },
              body: [buyingCriteria],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(buyingCriteriaPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details BuyingCriteria page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('buyingCriteria');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyingCriteriaPageUrlPattern);
      });

      it('edit button click should load edit BuyingCriteria page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BuyingCriteria');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyingCriteriaPageUrlPattern);
      });

      it('edit button click should load edit BuyingCriteria page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BuyingCriteria');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyingCriteriaPageUrlPattern);
      });

      it('last delete button click should delete instance of BuyingCriteria', () => {
        cy.intercept('GET', '/api/buying-criteria/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('buyingCriteria').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyingCriteriaPageUrlPattern);

        buyingCriteria = undefined;
      });
    });
  });

  describe('new BuyingCriteria page', () => {
    beforeEach(() => {
      cy.visit(`${buyingCriteriaPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BuyingCriteria');
    });

    it('should create an instance of BuyingCriteria', () => {
      cy.get(`[data-cy="value"]`).type('impactful Mali digital').should('have.value', 'impactful Mali digital');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        buyingCriteria = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', buyingCriteriaPageUrlPattern);
    });
  });
});
