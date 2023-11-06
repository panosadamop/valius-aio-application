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

describe('StatisticalError e2e test', () => {
  const statisticalErrorPageUrl = '/statistical-error';
  const statisticalErrorPageUrlPattern = new RegExp('/statistical-error(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const statisticalErrorSample = { value: 'deposit', language: 'ENGLISH' };

  let statisticalError;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/statistical-errors+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/statistical-errors').as('postEntityRequest');
    cy.intercept('DELETE', '/api/statistical-errors/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (statisticalError) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/statistical-errors/${statisticalError.id}`,
      }).then(() => {
        statisticalError = undefined;
      });
    }
  });

  it('StatisticalErrors menu should load StatisticalErrors page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('statistical-error');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('StatisticalError').should('exist');
    cy.url().should('match', statisticalErrorPageUrlPattern);
  });

  describe('StatisticalError page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(statisticalErrorPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create StatisticalError page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/statistical-error/new$'));
        cy.getEntityCreateUpdateHeading('StatisticalError');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', statisticalErrorPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/statistical-errors',
          body: statisticalErrorSample,
        }).then(({ body }) => {
          statisticalError = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/statistical-errors+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/statistical-errors?page=0&size=20>; rel="last",<http://localhost/api/statistical-errors?page=0&size=20>; rel="first"',
              },
              body: [statisticalError],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(statisticalErrorPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details StatisticalError page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('statisticalError');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', statisticalErrorPageUrlPattern);
      });

      it('edit button click should load edit StatisticalError page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('StatisticalError');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', statisticalErrorPageUrlPattern);
      });

      it('edit button click should load edit StatisticalError page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('StatisticalError');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', statisticalErrorPageUrlPattern);
      });

      it('last delete button click should delete instance of StatisticalError', () => {
        cy.intercept('GET', '/api/statistical-errors/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('statisticalError').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', statisticalErrorPageUrlPattern);

        statisticalError = undefined;
      });
    });
  });

  describe('new StatisticalError page', () => {
    beforeEach(() => {
      cy.visit(`${statisticalErrorPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('StatisticalError');
    });

    it('should create an instance of StatisticalError', () => {
      cy.get(`[data-cy="value"]`).type('invoice grid-enabled budgetary').should('have.value', 'invoice grid-enabled budgetary');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        statisticalError = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', statisticalErrorPageUrlPattern);
    });
  });
});
